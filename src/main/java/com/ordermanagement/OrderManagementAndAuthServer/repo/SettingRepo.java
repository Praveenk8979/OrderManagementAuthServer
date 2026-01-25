package com.ordermanagement.OrderManagementAndAuthServer.repo;

import com.ordermanagement.OrderManagementAndAuthServer.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepo extends JpaRepository<Setting,Long> {
}
