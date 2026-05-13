package com.srojsir.kittysaywoof.repository;

import com.srojsir.kittysaywoof.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {}
