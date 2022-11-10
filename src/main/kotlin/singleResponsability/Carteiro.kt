package singleResponsability

import java.util.logging.Handler
import kotlin.concurrent.thread

/**
 * @autor
 * @descricao: uma classe deve ter apenas uma responsabilidade unica, evitando criar god class (classes que fazem muitas coisas e sabem muito ao mesmo tempo)
 * fica difícil de dar manutenção em algo desse tipo, pois a gente pode dar manutenção e não saber o que de fato vai impactar, ou ficarmos com receio, visto que
 * essa alteraão pode danificar o comportamento que tinhamos antes.
 */

class Carteiro {
    private val carteiroRepository: CarteiroRepositorio = CarteiroRepositorio()

    fun entregarEncomenda() {
        carteiroRepository.salvarStatusEntrega(
            CarteiroModel(
                20,
                20,
                mutableListOf<Produtos>(Produtos("celular", "recanto das emas", "6555555-88", true))
            )
        )
    }

    fun voltarParaCentralDeAbastecimento() {

    }

    fun pegarEncomenda() {
        carteiroRepository.cadastrarProdutosAseremEntregues(
            CarteiroModel(
                20,
                20,
                mutableListOf<Produtos>(Produtos("celular", "recanto das emas", "6555555-88"))
            )
        )
    }
}

fun main() {
    val carteiro = Carteiro()
    carteiro.pegarEncomenda()
    println(CarteiroRepositorio.carteiroModel)
    println(CarteiroRepositorio.carteiroModel)
    carteiro.entregarEncomenda()

}


data class CarteiroModel(
    var quantidadeEntregaDisponivel: Int,
    var quantidadeProdutos: Int,
    var produtos: MutableList<Produtos>,

    )

data class Produtos(
    var nomeProdutos: String,
    var enderecoDoProdutoAserEntregue: String,
    var telefoneDoDestinario: String,
    var sucessoAoEntregar: Boolean = false
)


class CarteiroRepositorio {

    /**
     * @autor murillo
     * imagine que isso seja um BD listaProdutos
     */

    companion object {
        var carteiroModel = CarteiroModel(0, 0, mutableListOf())
    }

    fun salvarStatusEntrega(carteiro: CarteiroModel) {
        carteiro.produtos.forEach { produtos ->
            if (produtos.sucessoAoEntregar) {
                carteiro.produtos.remove(produtos)
            } else {
                devolverItemsParaCentral(produtos)
            }
        }
    }

    fun devolverItemsParaCentral(produtos: Produtos) {
        produtos.sucessoAoEntregar = false
    }

    fun cadastrarProdutosAseremEntregues(produtos: CarteiroModel) {
        carteiroModel = produtos
    }

}