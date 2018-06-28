package com.yale.sso.handler;

import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.yale.sso.crypto.CustomPasswordEncoder;
import com.yale.sso.model.SysUser;
@Component("customQueryDatabaseAuthenticationHandler")
public class CustomQueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

	@NotNull
	private String sql;
	
	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;
	
	@Override
	protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
		final String id = getPrincipalNameTransformer().transform(credential.getUsername());
		final String password = credential.getPassword();
		try {
			SysUser user = getJdbcTemplate().queryForObject(this.sql, new Object[]{id,id,id}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
			if (user == null) {
				throw new AccountNotFoundException(id + " not found with SQL query");
			}
			String pwd = user.getPassword();
			//用户名作为盐
			String salt=user.getUserName();
			final String encryptedPassword = customPasswordEncoder.encode(password,salt);
			if (!pwd.equals(encryptedPassword)) {
				throw new FailedLoginException("Password does not match value on record.");
			}
		} catch (final IncorrectResultSizeDataAccessException e) {
			if (e.getActualSize() == 0) {
				throw new AccountNotFoundException(id + " not found with SQL query");
			} else {
				throw new FailedLoginException("Multiple records found for " + id);
			}
		} catch (final DataAccessException e) {
			throw new PreventedException("SQL exception while executing query for " + id, e);
		}
		return createHandlerResult(credential, this.principalFactory.createPrincipal(id), null);
	}

	/**
     * @param sql: The sql to set.
     */
    @Autowired
    public void setSql(@Value("${cas.jdbc.authn.query.sql:}") final String sql){
        this.sql = sql;
    }

    @Override
    @Autowired(required = false)
    public void setDataSource(@Qualifier("queryDatabaseDataSource") final DataSource dataSource){
        super.setDataSource(dataSource);
    }


}
