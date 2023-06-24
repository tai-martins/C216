package br.inatel.labs.labjpa.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class NotaCompra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "notaCompra")
	private List<NotaCompraItem> listaNotaCompraItem;
	
	@ManyToOne
	private Fornecedor fornecedor;
	
	@NotNull
	@Past
	private LocalDate dataEmissao;
	
	public BigDecimal getCalculoTotalNota() {
		BigDecimal total = this.listaNotaCompraItem.stream()
				.map(i -> i.getCalculoTotalItem())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return total;
	}

}
