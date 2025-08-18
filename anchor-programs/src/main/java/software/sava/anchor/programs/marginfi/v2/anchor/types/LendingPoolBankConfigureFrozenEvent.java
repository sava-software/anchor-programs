package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LendingPoolBankConfigureFrozenEvent(GroupEventHeader header,
                                                  PublicKey bank,
                                                  PublicKey mint,
                                                  long depositLimit,
                                                  long borrowLimit) implements Borsh {

  public static LendingPoolBankConfigureFrozenEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var depositLimit = getInt64LE(_data, i);
    i += 8;
    final var borrowLimit = getInt64LE(_data, i);
    return new LendingPoolBankConfigureFrozenEvent(header,
                                                   bank,
                                                   mint,
                                                   depositLimit,
                                                   borrowLimit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, depositLimit);
    i += 8;
    putInt64LE(_data, i, borrowLimit);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header)
         + 32
         + 32
         + 8
         + 8;
  }
}
