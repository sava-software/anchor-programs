package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeProrataVaultParams(int poolType,
                                           PublicKey quoteMint,
                                           PublicKey baseMint,
                                           long depositingPoint,
                                           long startVestingPoint,
                                           long endVestingPoint,
                                           long maxBuyingCap,
                                           long escrowFee,
                                           int whitelistMode) implements Borsh {

  public static final int BYTES = 106;

  public static InitializeProrataVaultParams read(final byte[] _data, final int offset) {
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
    final var depositingPoint = getInt64LE(_data, i);
    i += 8;
    final var startVestingPoint = getInt64LE(_data, i);
    i += 8;
    final var endVestingPoint = getInt64LE(_data, i);
    i += 8;
    final var maxBuyingCap = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var whitelistMode = _data[i] & 0xFF;
    return new InitializeProrataVaultParams(poolType,
                                            quoteMint,
                                            baseMint,
                                            depositingPoint,
                                            startVestingPoint,
                                            endVestingPoint,
                                            maxBuyingCap,
                                            escrowFee,
                                            whitelistMode);
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
    putInt64LE(_data, i, depositingPoint);
    i += 8;
    putInt64LE(_data, i, startVestingPoint);
    i += 8;
    putInt64LE(_data, i, endVestingPoint);
    i += 8;
    putInt64LE(_data, i, maxBuyingCap);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    _data[i] = (byte) whitelistMode;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
