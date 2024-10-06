package com.fatec.dsm.tharseo.controllers.dtos.authenticate;

import com.fatec.dsm.tharseo.controllers.dtos.user.UserDtoResponse;
import com.fatec.dsm.tharseo.models.User;

public record AuthenticateResponse (UserDtoResponse user, String accessToken, Long expiresIn){

}
