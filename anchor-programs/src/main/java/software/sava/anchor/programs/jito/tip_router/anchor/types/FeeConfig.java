package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record FeeConfig(int blockEngineFeeBps,
                        PublicKey[] baseFeeWallets,
                        byte[] reserved,
                        Fees fee1,
                        Fees fee2) implements Borsh {

  public static final int BYTES = 722;

  public static FeeConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var blockEngineFeeBps = getInt16LE(_data, i);
    i += 2;
    final var baseFeeWallets = new PublicKey[8];
    i += Borsh.readArray(baseFeeWallets, _data, i);
    final var reserved = new byte[128];
    i += Borsh.readArray(reserved, _data, i);
    final var fee1 = Fees.read(_data, i);
    i += Borsh.len(fee1);
    final var fee2 = Fees.read(_data, i);
    return new FeeConfig(blockEngineFeeBps,
                         baseFeeWallets,
                         reserved,
                         fee1,
                         fee2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, blockEngineFeeBps);
    i += 2;
    i += Borsh.writeArray(baseFeeWallets, _data, i);
    i += Borsh.writeArray(reserved, _data, i);
    i += Borsh.write(fee1, _data, i);
    i += Borsh.write(fee2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
