package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProrataVaultCreated(PublicKey baseMint,
                                  PublicKey quoteMint,
                                  long startVestingSlot,
                                  long endVestingSlot,
                                  long maxBuyingCap,
                                  PublicKey pool,
                                  int poolType,
                                  long escrowFee) implements Borsh {

  public static final int BYTES = 129;

  public static ProrataVaultCreated read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var startVestingSlot = getInt64LE(_data, i);
    i += 8;
    final var endVestingSlot = getInt64LE(_data, i);
    i += 8;
    final var maxBuyingCap = getInt64LE(_data, i);
    i += 8;
    final var pool = readPubKey(_data, i);
    i += 32;
    final var poolType = _data[i] & 0xFF;
    ++i;
    final var escrowFee = getInt64LE(_data, i);
    return new ProrataVaultCreated(baseMint,
                                   quoteMint,
                                   startVestingSlot,
                                   endVestingSlot,
                                   maxBuyingCap,
                                   pool,
                                   poolType,
                                   escrowFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, startVestingSlot);
    i += 8;
    putInt64LE(_data, i, endVestingSlot);
    i += 8;
    putInt64LE(_data, i, maxBuyingCap);
    i += 8;
    pool.write(_data, i);
    i += 32;
    _data[i] = (byte) poolType;
    ++i;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}