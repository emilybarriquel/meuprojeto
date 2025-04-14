package com.exemplo.meuprojeto;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();

    @PostConstruct
    public void init() {
        produtos.add(new Produto(1L, "Produto A", 100.0, 10));
        produtos.add(new Produto(2L, "Produto B", 200.0, 5));
        produtos.add(new Produto(3L, "Produto C", 300.0, 12));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscarProdutos() {
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public Produto buscarProdutoById(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        class IdGenerator {
            private static long currentId = 0;

            public static synchronized long nextId() {
                return ++currentId;
            }
        }
        produto.setId(IdGenerator.nextId());
        produtos.add(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        boolean removed = produtos.removeIf(produto -> produto.getId().equals(id));
        if (removed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Código 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Código 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoEncontrado = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (produtoEncontrado != null) {
            produtoEncontrado.setNome(produto.getNome());
            produtoEncontrado.setPreco(produto.getPreco());
            produtoEncontrado.setEstoque(produto.getEstoque());
            return ResponseEntity.ok(produtoEncontrado); // Código 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Código 404 Not Found
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarParcialmenteProduto(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        Produto produtoEncontrado = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (produtoEncontrado != null) {
            fields.forEach((key, value) -> {
                switch (key) {
                    case "nome":
                        produtoEncontrado.setNome((String) value);
                        break;
                    case "preco":
                        produtoEncontrado.setPreco((Double) value);
                        break;
                    case "estoque":
                        produtoEncontrado.setEstoque((Integer) value);
                        break;
                }
            });
            return ResponseEntity.ok(produtoEncontrado); // Código 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Código 404 Not Found
        }
    }
}
