import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 séries
        String imdbKey = System.getenv("IMDB_API_KEY");
        String url = "https://imdb-api.com/en/API/MostPopularMovies/" + imdbKey;

        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);
        // extrair só os dados que interessam (título, poster classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Como Decorar o terminal:
        // https://www.alura.com.br/artigos/decorando-terminal-cores-emojis
        // O que ẽ JSON?: https://www.alura.com.br/artigos/o-que-e-json
        // VSCode para rodar JAVA:
        // https://www.alura.com.br/artigos/desenvolvendo-aplicacoes-java-vs-code
        // exibir e manipular os dados
        // System.out.println(listaDeFilmes.get(0));
        for (int i = 0; i < 3; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            // for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mURL da Imagem:\u001b[m " + filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            for (int j = 0; j < numeroEstrelinhas; j++) {
                System.out.print("⭐");
            }
            System.out.println("\n");
        }
        // Cria o diretõrio caso não exista
        var diretorio = new File("saida");
        diretorio.mkdir();

        var geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {
            String urlImagem = filme.get("image");
            String urlImagemMaior = urlImagem.replaceFirst("(@?\\.)([0-9A-Z,_]+).jpg$", "$1.jpg");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            InputStream imagemSobreposicao;
            String textoFigurinha;
            if (classificacao >= 8.0) {
                textoFigurinha = "TOPZERA";
                imagemSobreposicao = new FileInputStream(new File("sobreposicao/bom.jpg"));
            } else {
                textoFigurinha = "HMMMMM...";
                imagemSobreposicao = new FileInputStream(new File("sobreposicao/ruim.jpg"));
            }

            InputStream inputStream = new URL(urlImagemMaior).openStream();
            String nomeArquivo = "saida/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemSobreposicao);

            System.out.println(titulo);
            System.out.println();
            break;
        }

    }
}
