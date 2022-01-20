package com.tutorial.apidemo.database;


import com.tutorial.apidemo.enums.ERole;
import com.tutorial.apidemo.models.Role;
import com.tutorial.apidemo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HotelDatabase {
    @Autowired
    private RoleRepository roleRepository;
   /* @Autowired
    private LocationRepository locationRepository;*/
    @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role roleAdmin  = new Role(1, ERole.ROLE_ADMIN);
                Role roleMod=new Role(2, ERole.ROLE_MODERATOR);
                Role roleUser = new Role(3, ERole.ROLE_USER);
                List<Role> list=new ArrayList<Role>();
                list.add(roleAdmin); list.add(roleMod); list.add(roleUser);
                roleRepository.saveAll(list);

         /*  List<Location> locations=new ArrayList<Location>();
                locations.add(new Location(1,"Đà Nẵng","https://vcdn1-dulich.vnecdn.net/2020/07/01/shutterstock1169930359-1593591-8866-7555-1593591174.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=lSSNsc9gxo-3y6TVbCQAJQ"));
                locations.add(new Location(2,"Sài Gòn","https://vietsensetravel.com/view/at_gioi-thieu-ve-sai-gon_3ef0af3f1d84ab6cf38ed014a5fab4b7.jpg"));
                locations.add(new Location(3,"Hà Nội","https://owa.bestprice.vn/images/destinations/uploads/trung-tam-thanh-pho-ha-noi-603da1f235b38.jpg"));
                locations.add(new Location(4,"Thừa Thiên Huế","https://file1.dangcongsan.vn/data/0/images/2021/11/05/halthts/thua-thien-hue.jpg"));
                locations.add(new Location(5,"Đà Lạt","https://znews-photo.zadn.vn/w660/Uploaded/qhj_pwqvdvicbu/2019_03_23/THUMB.jpg"));
                locations.add(new Location(6,"Hội An","https://dulichannam.com/wp-content/uploads/2018/10/hoi_an_dem.jpg"));
                locationRepository.saveAll(locations);
*/
            }
        };

    }
}
