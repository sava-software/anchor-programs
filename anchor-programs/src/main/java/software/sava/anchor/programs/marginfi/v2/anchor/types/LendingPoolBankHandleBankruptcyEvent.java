package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;

public record LendingPoolBankHandleBankruptcyEvent(AccountEventHeader header,
                                                   PublicKey bank,
                                                   PublicKey mint,
                                                   double badDebt,
                                                   double coveredAmount,
                                                   double socializedAmount) implements Borsh {

  public static LendingPoolBankHandleBankruptcyEvent read(final byte[] _data, final int offset) {
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
    final var badDebt = getFloat64LE(_data, i);
    i += 8;
    final var coveredAmount = getFloat64LE(_data, i);
    i += 8;
    final var socializedAmount = getFloat64LE(_data, i);
    return new LendingPoolBankHandleBankruptcyEvent(header,
                                                    bank,
                                                    mint,
                                                    badDebt,
                                                    coveredAmount,
                                                    socializedAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putFloat64LE(_data, i, badDebt);
    i += 8;
    putFloat64LE(_data, i, coveredAmount);
    i += 8;
    putFloat64LE(_data, i, socializedAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header)
         + 32
         + 32
         + 8
         + 8
         + 8;
  }
}
