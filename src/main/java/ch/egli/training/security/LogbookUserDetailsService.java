package ch.egli.training.security;

import ch.egli.training.model.Benutzer;
import ch.egli.training.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LogbookUserDetailsService implements UserDetailsService {

	@Autowired
	private BenutzerRepository benutzerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Benutzer benutzer = benutzerRepository.findByBenutzername(username);
		
		if (benutzer == null) {
			throw new UsernameNotFoundException(String.format("User with the username %s doesn't exist", username));
		}
		
		// Create a granted authority based on user's role. 
		// Can't pass null authorities to user. Hence initialize with an empty ArrayList
		List<GrantedAuthority> authorities = new ArrayList<>();
        String rollen = benutzer.getRollen();

		if (!rollen.isEmpty()) {
            List<String> roles = Arrays.asList(rollen.split("\\s*,\\s*"));
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
		}
		
		// Create a UserDetails object from the data 
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(benutzer.getBenutzername(), benutzer.getPasswort(), authorities);
		
		return userDetails;
	}
}
