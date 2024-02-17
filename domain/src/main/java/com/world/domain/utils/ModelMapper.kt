package com.world.domain.utils

interface ModelMapper<DOMAIN, TO> {
    fun mapTo(model: DOMAIN): TO
    fun mapToDomain(model: TO): DOMAIN
}