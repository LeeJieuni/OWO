package com.sos.owo.domain;

import com.sos.owo.dto.MemberUpdateDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class Member implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name ="member_pw", nullable = false)
    private String pw;

    @Column(name="member_nick")
    private String nick;

    @Column(name = "member_age")
    private int age;

    @Column(name = "member_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_height")
    private double height;

    @Column(name = "member_weight")
    private double weight;

    @Column(name = "member_activity_num")
    private int activityNum;

    @Column(name = "member_activity_hour")
    private int activityHour;

    @Column(name = "member_point")
    private int point;

    @Column(name="member_secret")
    private boolean secret;

    @Column(name = "member_exp")
    private int exp;

    @Column(name = "member_joindate")
    private LocalDateTime joindate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_img_id")
    private ProfileImg profileImg;

    @Column(name = "member_enable")
    private boolean enable;

    @Builder.Default
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Goal> goalList = new ArrayList<>();


    @Column(name = "member_refresh_token")
    private String refreshToken;

    @Column(name = "member_activity_level")
    private int activityLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(name = "member_slogan")
    private String slogan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateMember(MemberUpdateDto memberUpdateDto){
        this.nick = memberUpdateDto.getNick();
        this.gender = memberUpdateDto.getGender();
        this.age = memberUpdateDto.getAge();
        this.height = memberUpdateDto.getHeight();
        this.weight = memberUpdateDto.getWeight();
        this.activityHour = memberUpdateDto.getActivityHour();
        this.activityNum = memberUpdateDto.getActivityNum();
        this.activityLevel = memberUpdateDto.getActivityLevel();

    }

    public void updateProfieImg(ProfileImg profileImg){
        this.profileImg = profileImg;
    }

    public void updateMemberSlogan(String slogan){
        this.slogan = slogan;
    }

    public Member update(String name){
        this.nick = name;
        return this;
    }

}