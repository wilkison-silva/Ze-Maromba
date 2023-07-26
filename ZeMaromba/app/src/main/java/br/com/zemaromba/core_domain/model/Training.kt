package br.com.zemaromba.core_domain.model

data class Training(
    val id: Long,
    val name: String,
    val sets: List<Set>
)