package local.poc.ollama.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inquiry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String inquiryText;
  private String category;
  private String priority;
  private boolean inScope;

  public Inquiry() {
  }

  public Inquiry(String inquiryText, String category, String priority, boolean inScope) {
      this.inquiryText = inquiryText;
      this.category = category;
      this.priority = priority;
      this.inScope = inScope;
  }

  public Long getId() {
      return id;
  }

  public String getInquiryText() {
      return inquiryText;
  }

  public String getCategory() {
      return category;
  }

  public String getPriority() {
      return priority;
  }

  public boolean getInScope() {
    return inScope;
  }
}
