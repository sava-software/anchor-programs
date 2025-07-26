package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

// Errors that can be raised while creating or manipulating a scope chain
public enum ScopeChainError implements Borsh.Enum {

  PriceChainTooLong,
  PriceChainConversionFailure,
  NoChainForToken,
  InvalidPricesInChain,
  MathOverflow,
  IntegerConversionOverflow;

  public static ScopeChainError read(final byte[] _data, final int offset) {
    return Borsh.read(ScopeChainError.values(), _data, offset);
  }
}