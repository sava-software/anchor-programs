package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record InitializeVaultWithConfigParams(int poolType,
                                              PublicKey quoteMint,
                                              PublicKey baseMint,
                                              // Permissioned vault support whitelist wallet deposit cap feature
                                              boolean permissioned) implements Borsh {

  public static final int BYTES = 66;

  public static InitializeVaultWithConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolType = _data[i] & 0xFF;
    ++i;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var permissioned = _data[i] == 1;
    return new InitializeVaultWithConfigParams(poolType,
                                               quoteMint,
                                               baseMint,
                                               permissioned);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) poolType;
    ++i;
    quoteMint.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    _data[i] = (byte) (permissioned ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}