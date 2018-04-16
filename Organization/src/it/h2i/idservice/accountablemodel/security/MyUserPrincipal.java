package it.h2i.idservice.accountablemodel.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.h2i.idservice.accountablemodel.model.Appertain;
import it.h2i.idservice.accountablemodel.model.User;
import it.h2i.idservice.accountablemodel.model.*;



public class MyUserPrincipal implements UserDetails {
	private User user;

	public MyUserPrincipal(User user) {
		this.user = user;
	}


	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		  List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority(user.getRole()));
	        System.out.println("        sd " +authorities.get(0));
	        return authorities;


	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getMail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getEnable();
	}
}