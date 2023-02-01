package com.attornatus.avaliacaobackend.br;

import com.attornatus.avaliacaobackend.br.model.endereco.Endereco;
import com.attornatus.avaliacaobackend.br.service.ViaCepService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class ClienteTest {
	@MockBean
	public ViaCepService viaCepService;

	@Test
	public void testBuscaEnderecoPorCep() {
		Endereco endereco = new Endereco();
		endereco.setCep("01001-000");
		endereco.setLocalidade("São Paulo");
		endereco.setUf("SP");

		when(viaCepService.consultarCep("01001-000")).thenReturn(endereco);

		Endereco result = viaCepService.consultarCep("01001-000");
		assertThat(result.getCep()).isEqualTo(endereco.getCep());
		assertThat(result.getLocalidade()).isEqualTo(endereco.getLocalidade());
		assertThat(result.getUf()).isEqualTo(endereco.getUf());
	}

}
