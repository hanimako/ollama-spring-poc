package local.poc.ollama;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class HomeController {

  private final InquiryService inquiryService;

  public HomeController(InquiryService inquiryService) {
    this.inquiryService = inquiryService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @PostMapping("/classify")
  public String classify(
        @RequestParam String inquiry,
        Model model) {

    if (inquiry.isBlank()) {
      model.addAttribute("error", "問い合わせを入力してください。");
      return "index";
    }

    model.addAttribute("inquiry", inquiry);

    try {
      ClassificationResult result = inquiryService.classify(inquiry);

      model.addAttribute("category", result.category());
      model.addAttribute("priority", result.priority());
    } catch(Exception e) {
      model.addAttribute("error", "AIによる分類に失敗しました。Ollamaが起動いてるか確認してください。");
    }

    return "index";
  }
}
