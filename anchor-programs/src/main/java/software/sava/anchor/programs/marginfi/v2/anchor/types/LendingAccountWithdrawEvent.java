package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LendingAccountWithdrawEvent(AccountEventHeader header,
                                          PublicKey bank,
                                          PublicKey mint,
                                          long amount,
                                          boolean closeBalance) implements Borsh {

  public static LendingAccountWithdrawEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = AccountEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var closeBalance = _data[i] == 1;
    return new LendingAccountWithdrawEvent(header,
                                           bank,
                                           mint,
                                           amount,
                                           closeBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (closeBalance ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header)
         + 32
         + 32
         + 8
         + 1;
  }
}
