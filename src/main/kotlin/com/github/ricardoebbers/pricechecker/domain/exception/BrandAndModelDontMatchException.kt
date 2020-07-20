package com.github.ricardoebbers.pricechecker.domain.exception

import org.springframework.http.HttpStatus

class BrandAndModelDontMatchException(
        actualBrandId: Int,
        informedBrandId: Int,
        modelId: Int
) : BusinessException(HttpStatus.BAD_REQUEST) {
    override val message = "Id '$informedBrandId' informado não corresponde ao id '$actualBrandId' da marca cadastrada para o modelo de veículo de id '$modelId'."

}
