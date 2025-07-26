package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record MintToScopeChain(PublicKey mint, short[] scopeChain) implements Borsh {

  public static final int BYTES = 40;
  public static final int SCOPE_CHAIN_LEN = 4;

  public static MintToScopeChain read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var scopeChain = new short[4];
    Borsh.readArray(scopeChain, _data, i);
    return new MintToScopeChain(mint, scopeChain);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    i += Borsh.writeArray(scopeChain, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
