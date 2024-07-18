package projeto_fullstack.api.resource.fornecimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import projeto_fullstack.api.repository.FornecimentoRepository;

@RestController
@RequestMapping(path = "/fornecimento")
@Slf4j
@CrossOrigin // apagar isso em producao. Deixar apenas para desenvolvimento
public class FornecimentoResource {
	@Autowired
	FornecimentoRepository fornecimentoRepository;

	@ResponseBody
	@PutMapping("delete")  // Nao eh interessante incluir um body em requisicoes delete, por isso a escolha do put
	public void delete(@RequestBody List<Long> ids) {
		fornecimentoRepository.deleteAllById(ids);
	}
}
