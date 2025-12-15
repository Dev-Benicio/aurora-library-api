package com.auroralibrary.library.dto.response;

public record AdministratorResponse(
        Long id,
        String name,
        String login,
        String profilePicture
) {
}
