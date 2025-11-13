package org.spring.tripreminder

import com.fasterxml.jackson.databind.ObjectMapper
import net.datafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import org.instancio.Instancio
import org.instancio.Select
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.GeometryFactory
import org.spring.tripreminder.dtos.UpdateReminderDTO
import org.spring.tripreminder.dtos.UpdateTripDTO
import org.spring.tripreminder.dtos.UpdateUserDTO
import org.spring.tripreminder.mappers.ReminderMapper
import org.spring.tripreminder.mappers.TripMapper
import org.spring.tripreminder.mappers.UserMapper
import org.spring.tripreminder.orms.Reminder
import org.spring.tripreminder.orms.Trip
import org.spring.tripreminder.orms.User
import org.spring.tripreminder.repositories.ReminderRepository
import org.spring.tripreminder.repositories.TripRepository
import org.spring.tripreminder.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Random
import java.util.concurrent.TimeUnit
import java.util.function.Supplier


@SpringBootTest
@AutoConfigureMockMvc
class TripControllerTest{

    val rand: Random = Random()

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userMapper: UserMapper
    @Autowired
    private lateinit var tripMapper: TripMapper
    @Autowired
    private lateinit var reminderMapper: ReminderMapper

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var tripRepository: TripRepository
    @Autowired
    private lateinit var reminderRepository: ReminderRepository

    @Autowired
    private lateinit var faker: Faker


    private lateinit var testUser: User

    private lateinit var testTrip1: Trip
    private lateinit var testTrip2: Trip

    private lateinit var testReminder1: Reminder
    private lateinit var testReminder2: Reminder

    val geometryFactory = GeometryFactory()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @BeforeEach
    fun setup() {
        testUser = Instancio.of(User::class.java)
            .ignore(Select.field(User::id))
            .supply(Select.field(User::username), Supplier { faker.credentials().username() })
            .supply(Select.field(User::password), Supplier { faker.credentials().password() })
            .supply(Select.field(User::timeOffset), Supplier { rand.nextInt(30) })
            .supply(Select.field(User::preferredTransport), Supplier { TransportType.WALK })
            .create()
                as User

        val originTrip1 = faker.address().city()
        val destinationTrip1 = faker.address().city()

        val originCordTrip1 = Coordinate(faker.address().longitude().toDouble(), faker.address().latitude().toDouble())
        val destinationCordTrip1 =
            Coordinate(faker.address().longitude().toDouble(), faker.address().latitude().toDouble())

        val originPointTrip1: Point = geometryFactory.createPoint(originCordTrip1)
        val destinationPointTrip1: Point = geometryFactory.createPoint(destinationCordTrip1)

        val plannedTimeStrTrip1 = faker.timeAndDate().future(1, TimeUnit.HOURS, "HH:mm")
        val arrivalTimeStrTrip1 = faker.timeAndDate().future(3, TimeUnit.HOURS, "HH:mm")

        val plannedTimeLocalTrip1 = LocalTime.parse(plannedTimeStrTrip1, formatter)
        val arrivalTimeLocalTrip1 = LocalTime.parse(arrivalTimeStrTrip1, formatter)

        val plannedDateTimeTrip1 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), plannedTimeLocalTrip1)
        val arrivalDateTimeTrip1 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), arrivalTimeLocalTrip1)

        testTrip1 = Instancio.of(Trip::class.java)
            .ignore(Select.field(Trip::id))
            .supply(Select.field(Trip::user), Supplier { testUser })
            .supply(
                Select.field(Trip::name),
                Supplier { "$originTrip1 - $destinationTrip1" })
            .supply(Select.field(Trip::origin), Supplier { originPointTrip1 })
            .supply(Select.field(Trip::destination), Supplier { destinationPointTrip1 })
            .supply(Select.field(Trip::plannedTime), Supplier { plannedDateTimeTrip1 })
            .supply(Select.field(Trip::arrivalTime), Supplier { arrivalDateTimeTrip1 })
            .supply(Select.field(Trip::transportType), Supplier { TransportType.WALK })
            .create()
                as Trip

        val originTrip2 = faker.address().city()
        val destinationTrip2 = faker.address().city()

        val originCordTrip2 = Coordinate(faker.address().longitude().toDouble(), faker.address().latitude().toDouble())
        val destinationCordTrip2 =
            Coordinate(faker.address().longitude().toDouble(), faker.address().latitude().toDouble())

        val originPointTrip2: Point = geometryFactory.createPoint(originCordTrip2)
        val destinationPointTrip2: Point = geometryFactory.createPoint(destinationCordTrip2)

        val plannedTimeStrTrip2 = faker.timeAndDate().future(1, TimeUnit.HOURS, "HH:mm")
        val arrivalTimeStrTrip2 = faker.timeAndDate().future(3, TimeUnit.HOURS, "HH:mm")

        val plannedTimeLocalTrip2 = LocalTime.parse(plannedTimeStrTrip2, formatter)
        val arrivalTimeLocalTrip2 = LocalTime.parse(arrivalTimeStrTrip2, formatter)

        val plannedDateTimeTrip2 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), plannedTimeLocalTrip2)
        val arrivalDateTimeTrip2 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), arrivalTimeLocalTrip2)

        testTrip2 = Instancio.of(Trip::class.java)
            .ignore(Select.field(Trip::id))
            .supply(Select.field(Trip::user), Supplier { testUser })
            .supply(
                Select.field(Trip::name),
                Supplier { "$originTrip2 - $destinationTrip2" })
            .supply(Select.field(Trip::origin), Supplier { originPointTrip2 })
            .supply(Select.field(Trip::destination), Supplier { destinationPointTrip2 })
            .supply(Select.field(Trip::plannedTime), Supplier { plannedDateTimeTrip2 })
            .supply(Select.field(Trip::arrivalTime), Supplier { arrivalDateTimeTrip2 })
            .supply(Select.field(Trip::transportType), Supplier { TransportType.WALK })
            .create()
                as Trip

        val notificationTimeStrReminder1 = faker.timeAndDate().future(1, TimeUnit.HOURS, "HH:mm")

        val notificationTimeLocalReminder1 = LocalTime.parse(notificationTimeStrReminder1, formatter)

        val notificationDateTimeReminder1 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), notificationTimeLocalReminder1)

        val isSent1 = rand.nextBoolean()

        testReminder1 = Instancio.of(Reminder::class.java)
            .ignore(Select.field(Reminder::id))
            .supply(Select.field(Reminder::trip), Supplier { testTrip1 })
            .supply(Select.field(Reminder::notificationTime), Supplier {notificationDateTimeReminder1})
            .supply(Select.field(Reminder::sent), Supplier {isSent1})
            .create()
                as Reminder

        val notificationTimeStrReminder2 = faker.timeAndDate().future(1, TimeUnit.HOURS, "HH:mm")

        val notificationTimeLocalReminder2 = LocalTime.parse(notificationTimeStrReminder2, formatter)

        val notificationDateTimeReminder2 = LocalDateTime.of(LocalDateTime.now().toLocalDate(), notificationTimeLocalReminder2)

        val isSent2 = rand.nextBoolean()

        testReminder2 = Instancio.of(Reminder::class.java)
            .ignore(Select.field(Reminder::id))
            .supply(Select.field(Reminder::trip), Supplier { testTrip1 })
            .supply(Select.field(Reminder::notificationTime), Supplier {notificationDateTimeReminder2})
            .supply(Select.field(Reminder::sent), Supplier {isSent2})
            .create()
                as Reminder

    }
    /**
     * Тест 1: Проверяет ТОЛЬКО создание пользователя.
     **/
    @Test
    @Transactional
    fun `test create user`(){
        val userDto = userMapper.toResponseDto(testUser)

        val result = mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.username").value(testUser.username))
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andReturn()

        val responseString = result.response.contentAsString
        val createdUserMap = objectMapper.readValue(responseString, Map::class.java)
        val createdUserId = (createdUserMap["id"] as Number).toLong()

        val user = userRepository.findById(createdUserId).get()
        assertNotNull(user)
        assertThat(user.username).isEqualTo(testUser.username)
    }

    /**
     * Тест 2: Проверяет ТОЛЬКО создание поездки.
     **/
    @Test
    @Transactional
    fun `test create trip`(){
        val savedUser = userRepository.save(testUser)

        testTrip1.user = savedUser
        val tripDto = tripMapper.toResponseDto(testTrip1)

        val result = mockMvc.perform(post("/api/trips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripDto)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value(testTrip1.name))
            .andExpect(jsonPath("$.user.id").value(savedUser.id))
            .andReturn()

        val responseString = result.response.contentAsString
        val createdTripMap = objectMapper.readValue(responseString, Map::class.java)
        val createdTripId = (createdTripMap["id"] as Number).toLong()

        val trip = tripRepository.findById(createdTripId).get()
        assertNotNull(trip)
        assertThat(trip.name).isEqualTo(testTrip1.name)
        assertThat(trip.user.id).isEqualTo(savedUser.id)
    }

    /**
     * Тест 3: Проверяет ТОЛЬКО создание напоминания.
     **/
    @Test
    @Transactional
    fun `test create reminder`(){
        val savedUser = userRepository.save(testUser)
        testTrip1.user = savedUser
        val savedTrip = tripRepository.save(testTrip1)

        testReminder1.trip = savedTrip
        val reminderDto = reminderMapper.toResponseDto(testReminder1)

        val result = mockMvc.perform(post("/api/reminders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reminderDto)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.trip.id").value(savedTrip.id))
            .andReturn()

        val responseString = result.response.contentAsString
        val createdReminderMap = objectMapper.readValue(responseString, Map::class.java)
        val createdReminderId = (createdReminderMap["id"] as Number).toLong()

        val reminder = reminderRepository.findById(createdReminderId).get()
        assertNotNull(reminder)
        assertThat(reminder.trip.id).isEqualTo(savedTrip.id)
    }

    @Test
    @Transactional
    fun `test update user`() {
        userRepository.save(testUser)

        val dto = UpdateUserDTO(timeOffset = 5)

        val request = put("/api/users/${testUser.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))

        mockMvc.perform(request).andExpect(status().isOk)

        val user = userRepository.findById(testUser.id).get()
        assertThat(user.timeOffset).isEqualTo(dto.timeOffset)
    }

    @Test
    @Transactional
    fun `test update trip`(){
        tripRepository.save(testTrip1)

        val dto = UpdateTripDTO(name = "Тарутинский маневр")

        val request = put("/api/trips/${testTrip1.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))

        mockMvc.perform(request).andExpect(status().isOk)

        val trip1 = tripRepository.findById(testTrip1.id).get()
        assertThat(trip1.name).isEqualTo(dto.name)
    }

    @Test
    @Transactional
    fun `test update reminder`(){
        reminderRepository.save(testReminder1)

        val dto = UpdateReminderDTO(sent = true)

        val request = put("/api/reminders/${testReminder1.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))

        mockMvc.perform(request).andExpect(status().isOk)

        val reminder1 = reminderRepository.findById(testReminder1.id).get()
        assertThat(reminder1.sent).isEqualTo(dto.sent)
    }

    @Test
    @Transactional
    fun `test user show`(){
        userRepository.save(testUser)

        val request = get("/api/users/${testUser.id}")
        val result = mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        assertThatJson(body).and(
            { v -> v.node("username").isEqualTo(testUser.username) },
            { v -> v.node("timeOffset").isEqualTo((testUser.timeOffset).toString()) },
            { v -> v.node("preferredTransport").isEqualTo(testUser.preferredTransport)}
        )
    }

    @Test
    @Transactional
    fun `test trip show`(){
        tripRepository.save(testTrip1)

        val request = get("/api/trips/${testTrip1.id}")
        val result = mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        assertThatJson(body).and(
            { v -> v.node("name").isEqualTo(testTrip1.name) },
            { v -> v.node("origin").isEqualTo(testTrip1.origin) },
            { v -> v.node("destination").isEqualTo(testTrip1.destination) },
            { v -> v.node("plannedTime").isEqualTo(testTrip1.plannedTime) },
            { v -> v.node("arrivalTime").isEqualTo(testTrip1.arrivalTime) },
            { v -> v.node("transportType").isEqualTo(testTrip1.transportType) }
        )
    }

    @Test
    @Transactional
    fun `test reminder show`(){
        reminderRepository.save(testReminder1)

        val request = get("/api/users/${testReminder1.id}")
        val result = mockMvc
            .perform(request)
            .andExpect(status().isOk)
            .andReturn()

        val body = result.response.contentAsString
        assertThatJson(body).and(
            {v -> v.node("tripId").isEqualTo(testTrip1.id)},
            {v -> v.node("notificationTime").isEqualTo(testReminder1.notificationTime) },
        )
    }

    @Test
    @Transactional
    fun `test user destroy`(){
        userRepository.save(testUser)

        val request = delete("/api/users/${testUser.id}")
        mockMvc
            .perform(request)
            .andExpect(status().isNoContent)

        assertThat(userRepository.existsById(testUser.id)).isFalse()
    }

    @Test
    @Transactional
    fun `test trip destroy`(){
        tripRepository.save(testTrip1)

        val request = delete("/api/trips/${testTrip1.id}")
        mockMvc
            .perform(request)
            .andExpect(status().isNoContent)

        assertThat(tripRepository.existsById(testTrip1.id)).isFalse()
    }

    @Test
    @Transactional
    fun `test reminder destroy`(){
        reminderRepository.save(testReminder1)

        val request = delete("/api/reminders/${testReminder1.id}")
        mockMvc
            .perform(request)
            .andExpect(status().isNoContent)

        assertThat(reminderRepository.existsById(testReminder1.id)).isFalse()
    }
}