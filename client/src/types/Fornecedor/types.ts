export interface FornecedorDataFields {
    cadastro: string,
    rg?: string | null,
    dataNascimento?: string | null,
    nome: string,
    email: string,
    cep: string
}

export interface Fornecedor extends FornecedorDataFields {
    id: number
}

export interface FornecimentoFornecedor {
    id: number,
    empresaId: number,
    empresaNome: string
}

export interface GetFornecedorResponse {
    fornecedor: Fornecedor,
    fornecimentos: FornecimentoFornecedor[] | null
}

export type GetFornecedoresResponse = Fornecedor[];

export interface PutFornecedorRequest extends Fornecedor {}

export interface PostFornecedorRequest {
    fornecedor: FornecedorDataFields,
    empresaIds?: number[]
}
