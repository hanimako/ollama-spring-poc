package local.poc.ollama;

import org.springframework.stereotype.Service;

import local.poc.ollama.entity.Inquiry;
import local.poc.ollama.repository.InquiryRepository;

@Service
public class InquiryService {

  private final OllamaService ollamaService;
  private final InquiryRepository inquiryRepository;

  public InquiryService(
      OllamaService ollamaService,
      InquiryRepository inquiryRepository) {

    this.ollamaService = ollamaService;
    this.inquiryRepository = inquiryRepository;
  }

  public ClassificationResult classify(String inquiry) throws Exception {

    ClassificationResult result = ollamaService.classify(inquiry);

    Inquiry inquiryEntity = new Inquiry(
        inquiry,
        result.category(),
        result.priority(),
        result.inScope()
    );

    inquiryRepository.save(inquiryEntity);

    return result;
  }
}
