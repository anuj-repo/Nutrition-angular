package com.fertilizer.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_clients")
@DynamicUpdate
@Getter
@Setter
public class UserClients extends BaseModel{

    private static final long serialVersionUID = -6160224319263786985L;
    private Long userId;
    private long clientId;

}
