package com.czertainly.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class UserDetailDto extends UserDto {

    @Schema(description = "Roles for the user")
    private List<RoleDto> roles;

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", getUuid())
                .append("username", getUsername())
                .append("firstName", getFirstName())
                .append("lastName", getLastName())
                .append("email", getEmail())
                .append("certificate", getCertificate())
                .append("enabled", getEnabled())
                .append("roles", roles)
                .toString();
    }
}