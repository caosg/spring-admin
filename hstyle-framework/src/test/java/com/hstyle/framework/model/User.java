package com.hstyle.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import com.hstyle.framework.model.IdEntity;
import com.hstyle.framework.model.LabelValue;

/**
 *
 */
@Entity
@Table(name = "app_user")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "FindUserByUsernameAndPassword", query = "select u from User u where u.username = :username and u.password = :password") })
public class User extends IdEntity implements Serializable {
	private static final long serialVersionUID = 3832626162173359411L;
	private String username; // required
	private String password; // required
	private String confirmPassword;
	private String passwordHint;
	private String firstName; // required
	private String lastName; // required
	private String email; // required; unique
	private String phoneNumber;
	private String website;
	private Address address = new Address();
	private Integer version;
	private Set<Role> roles = new HashSet<Role>();
	private boolean enabled;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public User() {
	}

	/**
	 * Create a new instance and set the username.
	 * 
	 * @param username
	 *            login name for user.
	 */
	public User(final String username) {
		this.username = username;
	}

	@Column(nullable = false, length = 50, unique = true)

	public String getUsername() {
		return username;
	}

	@Column(nullable = false)
	@XmlTransient
	public String getPassword() {
		return password;
	}

	@Transient
	@XmlTransient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@Column(name = "password_hint")
	@XmlTransient
	public String getPasswordHint() {
		return passwordHint;
	}

	@Column(name = "first_name", nullable = false, length = 50)

	public String getFirstName() {
		return firstName;
	}

	@Column(name = "last_name", nullable = false, length = 50)

	public String getLastName() {
		return lastName;
	}

	@Column(nullable = false, unique = true)

	public String getEmail() {
		return email;
	}

	@Column(name = "phone_number")

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getWebsite() {
		return website;
	}

	/**
	 * Returns the full name.
	 * 
	 * @return firstName + ' ' + lastName
	 */
	@Transient
	public String getFullName() {
		return firstName + ' ' + lastName;
	}

	@Embedded

	public Address getAddress() {
		return address;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Fetch(FetchMode.SUBSELECT)
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Convert user roles to LabelValue objects for convenience.
	 * 
	 * @return a list of LabelValue objects with role information
	 */
	@Transient
	public List<LabelValue> getRoleList() {
		List<LabelValue> userRoles = new ArrayList<LabelValue>();

		if (this.roles != null) {
			for (Role role : roles) {
				// convert the user's roles to LabelValue Objects
				userRoles.add(new LabelValue(role.getName(), role.getName()));
			}
		}

		return userRoles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role) {
		getRoles().add(role);
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	@Column(name = "account_enabled")
	public boolean isEnabled() {
		return enabled;
	}

	@Column(name = "account_expired", nullable = false)
	public boolean isAccountExpired() {
		return accountExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 * @return true if account is still active
	 */
	@Transient
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	@Column(name = "account_locked", nullable = false)
	public boolean isAccountLocked() {
		return accountLocked;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 * @return false if account is locked
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	@Column(name = "credentials_expired", nullable = false)
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 * @return true if credentials haven't expired
	 */
	@Transient
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		final User user = (User) o;

		return !(username != null ? !username.equals(user.getUsername()) : user
				.getUsername() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("username", this.username)
				.append("enabled", this.enabled)
				.append("accountExpired", this.accountExpired)
				.append("credentialsExpired", this.credentialsExpired)
				.append("accountLocked", this.accountLocked);

		if (roles != null) {
			sb.append("Granted Authorities: ");

			int i = 0;
			for (Role role : roles) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(role.toString());
				i++;
			}
		} else {
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}
}
