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
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        // String url =
        // "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-06-14";
        // ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";
            InputStream imagemSobreposicao = new FileInputStream(new File("sobreposicao/bom.jpg"));
            geradora.cria(inputStream, nomeArquivo, "TOPZERA", imagemSobreposicao);

            System.out.println(conteudo.getTitulo());
            System.out.println();
        }

        // URI endereco = URI.create(url);
        // var client = HttpClient.newHttpClient();

        // var request = HttpRequest.newBuilder(endereco).GET().build();
        // HttpResponse<String> response = client.send(request,
        // BodyHandlers.ofString());
        // String body = response.body();
        // // System.out.println(body);
        // // extrair só os dados que interessam (título, poster classificação)
        // var parser = new JsonParser();
        // List<Map<String, String>> listaDeConteudos = parser.parse(body);

        // // Como Decorar o terminal:
        // // https://www.alura.com.br/artigos/decorando-terminal-cores-emojis
        // // O que ẽ JSON?: https://www.alura.com.br/artigos/o-que-e-json
        // // VSCode para rodar JAVA:
        // // https://www.alura.com.br/artigos/desenvolvendo-aplicacoes-java-vs-code
        // // exibir e manipular os dados
        // // System.out.println(listaDeConteudos.get(0));
        // for (int i = 0; i < 3; i++) {
        // Map<String, String> conteudo = listaDeConteudos.get(i);
        // // for (Map<String, String> conteudo : listaDeConteudos) {
        // System.out.println("\u001b[1mTítulo:\u001b[m " + conteudo.get("title"));
        // System.out.println("\u001b[1mURL da Imagem:\u001b[m " +
        // conteudo.get("image"));
        // System.out.println(conteudo.get("imDbRating"));
        // double classificacao = Double.parseDouble(conteudo.get("imDbRating"));
        // int numeroEstrelinhas = (int) classificacao;
        // for (int j = 0; j < numeroEstrelinhas; j++) {
        // System.out.print("⭐");
        // }
        // System.out.println("\n");
        // }
        // // Cria o diretõrio caso não exista
        // var diretorio = new File("saida");
        // diretorio.mkdir();

        // var geradora = new GeradoraDeFigurinhas();
        // for (Map<String, String> filme : listaDeConteudos) {
        // String urlImagem = filme.get("image");
        // String urlImagemMaior = urlImagem.replaceFirst("(@?\\.)([0-9A-Z,_]+).jpg$",
        // "$1.jpg");
        // String titulo = filme.get("title");
        // double classificacao = Double.parseDouble(filme.get("imDbRating"));

        // String json = http.buscaDados(url);
        // InputStream imagemSobreposicao;
        // String textoFigurinha;
        // if (classificacao >= 8.0) {
        // textoFigurinha = "TOPZERA";
        // imagemSobreposicao = new FileInputStream(new File("sobreposicao/bom.jpg"));
        // } else {
        // textoFigurinha = "HMMMMM...";
        // imagemSobreposicao = new FileInputStream(new File("sobreposicao/ruim.jpg"));
        // }

        // InputStream inputStream = new URL(urlImagemMaior).openStream();
        // String nomeArquivo = "saida/" + titulo + ".png";

        // geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemSobreposicao);

        // System.out.println(titulo);
        // System.out.println();
        // break;
        // }

    }
}
