package local.poc.ollama;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final ObjectMapper mapper = new ObjectMapper();

  public ClassificationResult classify(String inquiry) throws Exception {

    String content = """
          次の問い合わせを分類してください。
          このシステムはMicrosoft 365に関する問い合わせを対象としています。
          問い合わせがMicrosoft 365に関係しない場合は、対象外と判定してください。

          分類基準：
          質問＝使用方法や仕様について尋ねている。
          障害＝正常に利用できない、エラーが発生している。
          要望＝機能追加や改善を希望している。

          優先度基準：
          高＝直ちに対応が必要、業務停止中、または誤操作により重大な影響が生じる可能性がある。
          中＝業務に支障があるが、代替手段がある、または緊急性が低い。
          低＝業務への影響が小さく、急いで対応する必要がない。

          問い合わせ：
          %s

          /no_think
          """.formatted(inquiry);

    String contentJson = mapper.writeValueAsString(content);

    String json = """
          {
            "model": "qwen3:4b",
            "messages": [
              {
                "role": "user",
                "content": %s
              }
            ],
            "stream": false,
            "format": {
              "type": "object",
              "properties": {
                "category": {
                  "type": "string",
                  "enum": ["質問", "障害", "要望"]
                },
                "priority": {
                  "type": "string",
                  "enum": ["低", "中", "高"]
                },
                "inScope": {
                  "type": "boolean"
                }
              },
              "required": ["category", "priority", "inScope"]
            }
          }
          """.formatted(contentJson);

    HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("http://localhost:11434/api/chat"))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(json))
          .build();

    HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    JsonNode root = mapper.readTree(response.body());

    String responseContent = root
          .path("message")
          .path("content")
          .stringValue();

    JsonNode result = mapper.readTree(responseContent);

    String category = result.path("category").stringValue();
    String priority = result.path("priority").stringValue();
    boolean inScope = result.path("inScope").booleanValue();

    return new ClassificationResult(category, priority, inScope);
  }
}
