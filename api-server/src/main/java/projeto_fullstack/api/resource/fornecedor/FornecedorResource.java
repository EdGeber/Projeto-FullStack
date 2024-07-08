package projeto_fullstack.api.resource.fornecedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import projeto_fullstack.api.entity.Fornecedor;
import projeto_fullstack.api.entity.Fornecimento;
import projeto_fullstack.api.error.ErrorObject;
import projeto_fullstack.api.repository.FornecedorRepository;
import projeto_fullstack.api.repository.FornecimentoRepository;

@RestController
@RequestMapping(path = "/fornecedor")
@Slf4j
@CrossOrigin // apagar isso em producao. Deixar apenas para desenvolvimento
public class FornecedorResource {
	@Autowired
	MessageSource messageSource;
	@Autowired
	public static FornecedorRepository fornecedorRepository;
	@Autowired
	public static FornecimentoRepository fornecimentoRepository;

	@ResponseBody
	@PostMapping(path = "")
	public PostFornecedorResponse create(@Valid @RequestBody PostFornecedorRequest reqObj) {
		Fornecedor fornecedor = reqObj.getFornecedor();
		List<Long> empresaIds = reqObj.getEmpresaIds();
		Long id = fornecedorRepository.save(fornecedor).getId();
		List<Long> fornecimentoIds = null;
		if (empresaIds != null) {
			fornecimentoIds = addEmpresas(id, empresaIds);
		}
		return new PostFornecedorResponse(id, fornecimentoIds);
	}

	@ResponseBody
	@PostMapping(path = "{id}/empresas")
	public List<Long> addEmpresas(@PathVariable("id") Long id, @RequestBody List<Long> empresaIds) {
		if (!fornecedorRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		List<Fornecimento> fornecimentos = new ArrayList<>();
		for (Long empresaId : empresaIds) {
			Fornecimento fornecimento = new Fornecimento();
			fornecimento.setFornecedorId(id);
			fornecimento.setEmpresaId(empresaId);
			fornecimentos.add(fornecimento);
		}
		fornecimentos = fornecimentoRepository.saveAll(fornecimentos);
		return fornecimentos.stream().map(f -> f.getId()).collect(Collectors.toList());
	}

	@ResponseBody
	@PutMapping(path = "{id}")
	public ResponseEntity<Object> put(@PathVariable("id") Long id, @Valid @RequestBody Fornecedor fornecedor) {
		if (id != fornecedor.getId()) {
			Map<String, String> fieldErrors = new HashMap<>();
			List<String> businessErrors = new ArrayList<String>();
			// TODO usar locale da requisicao
			businessErrors.add(messageSource.getMessage("idsDontMatch", null, Locale.of("PT")));
			return new ResponseEntity<>(new ErrorObject(fieldErrors, businessErrors), HttpStatus.BAD_REQUEST);
		}
		fornecedor = fornecedorRepository.save(fornecedor);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(path = "{id}")
	public GetFornecedorResponse findById(@PathVariable("id") Long id,
			@RequestParam(name = "empresas", required = false, defaultValue = "false") boolean empresas) {
		Optional<Fornecedor> result = fornecedorRepository.findById(id);
		if (result.isPresent()) {
			Fornecedor fornecedor = result.get();
			List<Fornecimento> fornecimentos = null;
			if (empresas) {
				fornecimentos = fornecimentoRepository.getEmpresas(id);
			}
			return new GetFornecedorResponse(fornecedor, fornecimentos);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// isso fica lento se a tabela ficar muito grande
	@ResponseBody
	@GetMapping(path = "count")
	public Long getCount() {
		return fornecedorRepository.count();
	}

	@ResponseBody
	@GetMapping(path = "page")
	public Iterable<Fornecedor> getPage(
			@RequestParam(name = "from", required = false, defaultValue = ValueConstants.DEFAULT_NONE) String from) {
		final int PAGE_SIZE = 10;
		if (from == null) {
			return fornecedorRepository.getFirstPage(PAGE_SIZE);
		} else {
			return fornecedorRepository.getPage(from, PAGE_SIZE);
		}
	}

	@ResponseBody
	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable("id") Long id) {
		if (!fornecedorRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		fornecedorRepository.deleteById(id);
	}
}
