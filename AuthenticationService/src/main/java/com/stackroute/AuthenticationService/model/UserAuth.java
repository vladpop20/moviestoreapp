package com.stackroute.AuthenticationService.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuth {

	@Id
	@Email(message = "The email provided is not valid. Try again")
	@NotBlank
	private String email;

	@NotBlank(message = "The password is mandatory")
	private String password;

	@NotBlank(message = "The type is mandatory")
	@Pattern(regexp = "^(admin|customer)$", message = "Invalid user type")
	private String type;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserAuth))
			return false;

		UserAuth userAuth = (UserAuth) o;

		if (!email.equals(userAuth.email))
			return false;
		if (!password.equals(userAuth.password))
			return false;
		return type.equals(userAuth.type);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
