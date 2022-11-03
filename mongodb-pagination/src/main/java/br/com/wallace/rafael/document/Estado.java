package br.com.wallace.rafael.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estados")
public class Estado {

    @Id
    private Sigla sigla;
    private String estado;
    private String capital;

    enum Sigla {
        

        Acre                AC	Rio Branco
        Alagoas             AL	Maceió
        Amapá               AP	Macapá
        Amazonas            AM	Manaus
        Bahia               BA	Salvador
        Ceará               CE	Fortaleza
        Espírito Santo      ES	Vitória
        Goiás               GO	Goiânia
        Maranhão            MA	São Luís
        Mato Grosso         MT	Cuiabá
        Mato Grosso do Sul  MS	Campo Grande
        Minas Gerais        MG	Belo Horizonte
        Pará                PA	Belém
        Paraíba             PB	João Pessoa
        Paraná              PR	Curitiba
        Pernambuco          PE	Recife
        Piauí               PI	Teresina
        Rio de Janeiro      RJ	Rio de Janeiro
        Rio Grande do Norte RN	Natal
        Rio Grande do Sul   RS	Porto Alegre
        Rondônia            RO	Porto Velho
        Roraima             RR	Boa Vista
        Santa Catarina      SC	Florianópolis
        São Paulo           SP	São Paulo
        Sergipe             SE	Aracaju
        Tocantins           TO	Palmas
    }
}
