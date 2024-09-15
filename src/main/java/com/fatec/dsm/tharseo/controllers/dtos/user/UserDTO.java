package com.fatec.dsm.tharseo.controllers.dtos.user;


import com.fatec.dsm.tharseo.models.User;

public class UserDTO {

    public static class UserResponse {

        private Long id;
        private String email;


        public UserResponse() {
        }

        public UserResponse(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
        }


    }
}
