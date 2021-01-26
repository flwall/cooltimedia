package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<Audio,Long> {
}
