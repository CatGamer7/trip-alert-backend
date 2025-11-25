package org.spring.tripreminder.orms

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.spring.tripreminder.TransportType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    val id: Long = 0,

    @field:Column(name = "username", nullable = false, unique = true)
    private val username: String,

    @field:Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "time_offset", nullable = false)
    var timeOffset: Int,

    @Column(name = "preferred_transport", nullable = false)
    var preferredTransport: TransportType = TransportType.WALK,
): UserDetails {

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL]
    )
    val trips: MutableSet<Trip> = mutableSetOf()


    override fun getAuthorities(): Collection<GrantedAuthority?>? = ArrayList<GrantedAuthority>()

    override fun getUsername(): String = username
    override fun getPassword(): String = password
    fun setPassword(newPassword: String) {
        this.password = newPassword
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}