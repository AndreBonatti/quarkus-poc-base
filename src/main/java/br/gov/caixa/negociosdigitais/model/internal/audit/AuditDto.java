package br.gov.caixa.negociosdigitais.model.internal.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.ws.rs.core.MultivaluedMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {

    private String traceId;

    @JsonProperty("req-headers")
    private MultivaluedMap<String, String> reqHeaders;

    @JsonProperty("req-body")
    private Object reqBody;

    @JsonProperty("req-url")
    private String reqUrl;

    @JsonProperty("req-base-url")
    private String reqBaseUrl;

    @JsonProperty("req-method")
    private String reqMethod;

    @JsonProperty("req-time")
    private Object reqTime;

    @JsonProperty("req-duration")
    private Long reqDuration;

    // ============================ \\

    @JsonProperty("rsp-status")
    private String rspStatus;

    @JsonProperty("rsp-body")
    private Object rspBody;

    @JsonProperty("rsp-headers")
    private MultivaluedMap<String, Object> rspHeaders;


}
