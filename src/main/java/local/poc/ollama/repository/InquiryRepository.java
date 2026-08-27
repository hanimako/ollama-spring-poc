package local.poc.ollama.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import local.poc.ollama.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
