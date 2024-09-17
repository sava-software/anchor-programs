package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeFcfsVaultParams(int poolType,
                                        PublicKey quoteMint,
                                        PublicKey baseMint,
                                        long depositingSlot,
                                        long startVestingSlot,
                                        long endVestingSlot,
                                        long maxDepositingCap,
                                        long individualDepositingCap,
                                        long escrowFee,
                                        boolean permissioned) implements Borsh {

  public static final int BYTES = 114;

  public static InitializeFcfsVaultParams read(final byte[] _data, final int offset) {
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
    final var depositingSlot = getInt64LE(_data, i);
    i += 8;
    final var startVestingSlot = getInt64LE(_data, i);
    i += 8;
    final var endVestingSlot = getInt64LE(_data, i);
    i += 8;
    final var maxDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var individualDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var permissioned = _data[i] == 1;
    return new InitializeFcfsVaultParams(poolType,
                                         quoteMint,
                                         baseMint,
                                         depositingSlot,
                                         startVestingSlot,
                                         endVestingSlot,
                                         maxDepositingCap,
                                         individualDepositingCap,
                                         escrowFee,
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
    putInt64LE(_data, i, depositingSlot);
    i += 8;
    putInt64LE(_data, i, startVestingSlot);
    i += 8;
    putInt64LE(_data, i, endVestingSlot);
    i += 8;
    putInt64LE(_data, i, maxDepositingCap);
    i += 8;
    putInt64LE(_data, i, individualDepositingCap);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    _data[i] = (byte) (permissioned ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
