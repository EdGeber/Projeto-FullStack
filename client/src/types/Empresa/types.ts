export interface EmpresaDataFields {
    cnpj: string,
    nomeFantasia: string,
    cep: string
}

export interface Empresa extends EmpresaDataFields {
    id: number
}

export interface GetEmpresaResponse {
    empresa: Empresa,
    fornecimentos: FornecimentoEmpresa[] | null
}

export interface PutEmpresaRequest extends Empresa {}

export interface PostEmpresaRequest {
    empresa: EmpresaDataFields,
    fornecedorIds?: number[]
}

export interface FornecimentoEmpresa {
    id: number,
    fornecedorId: string,
    fornecedorNome: string
}
