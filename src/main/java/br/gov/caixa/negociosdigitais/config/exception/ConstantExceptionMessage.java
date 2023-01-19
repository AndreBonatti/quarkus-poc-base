package br.gov.caixa.negociosdigitais.config.exception;

public class ConstantExceptionMessage {

    private ConstantExceptionMessage() {
    }

    public static final String BAD_REQUEST_400 = "A requisição foi malformada, omitindo atributos obrigatórios, seja no payload ou através de atributos na URL.";
    public static final String BAD_REQUEST_HTML_INJECTION_400 = "Ataque de HTML Injection detectado.";
    public static final String UNAUTHORIZED_401 = "Cabeçalho de autenticação ausente/inválido ou token inválido";
    public static final String FORBIDDEN_403 = "O token tem escopo incorreto ou uma política de segurança foi violada";
    public static final String NOT_FOUND_404 = "O recurso solicitado não existe ou não foi encontrado";
    public static final String METHOD_NOT_ALLOWED_405 = "O consumidor tentou acessar o recurso com um método não suportado";
    public static final String NOT_ACCEPTABLE_406 = "A solicitação continha um cabeçalho Accept diferente dos tipos de mídia permitidos ou um conjunto de caracteres diferente de UTF-8";
    public static final String GONE_410 = "Não está mais disponível";
    public static final String UNSUPPORTED_MEDIA_TYPE_415 = "A operação foi recusada porque o payload está em um formato não suportado pelo endpoint";
    public static final String UNPROCESSABLE_ENTITY_422 = "A solicitação foi bem formada, mas não pôde ser processada devido à lógica de negócios específica da solicitação";
    public static final String TOO_MANY_REQUESTS_429 = "operação foi recusada, pois muitas solicitações foram feitas dentro de um determinado período ou o limite global de requisições concorrentes foi atingido";
    public static final String INTERNAL_SERVER_ERROR_500 = "Ocorreu um erro no gateway da API ou no microsserviço";
    public static final String SEVICE_UNAVAILABLE_503 = "O serviço está indisponível no momento";
    public static final String SEVICE_UNAVAILABLE_DATABASE_503 = "O serviço está indisponível no momento! Database DOWN.";
    public static final String GATEWAY_TIMEOUT_504 = "O servidor não pôde responder em tempo hábil";

}
