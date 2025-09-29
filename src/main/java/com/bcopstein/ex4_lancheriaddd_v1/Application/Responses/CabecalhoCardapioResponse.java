package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.MenuHeader;

public record CabecalhoCardapioResponse(List<MenuHeader> cabecalhos) {
}
