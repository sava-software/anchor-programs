package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record PerpBankruptcyRecord(int marketIndex,
                                   BigInteger pnl,
                                   BigInteger ifPayment,
                                   PublicKey clawbackUser,
                                   BigInteger clawbackUserPayment,
                                   BigInteger cumulativeFundingRateDelta) implements Borsh {

  public static PerpBankruptcyRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var pnl = getInt128LE(_data, i);
    i += 16;
    final var ifPayment = getInt128LE(_data, i);
    i += 16;
    final var clawbackUser = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (clawbackUser != null) {
      i += 32;
    }
    final var clawbackUserPayment = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (clawbackUserPayment != null) {
      i += 16;
    }
    final var cumulativeFundingRateDelta = getInt128LE(_data, i);
    return new PerpBankruptcyRecord(marketIndex,
                                    pnl,
                                    ifPayment,
                                    clawbackUser,
                                    clawbackUserPayment,
                                    cumulativeFundingRateDelta);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, pnl);
    i += 16;
    putInt128LE(_data, i, ifPayment);
    i += 16;
    i += Borsh.writeOptional(clawbackUser, _data, i);
    i += Borsh.writeOptional(clawbackUserPayment, _data, i);
    putInt128LE(_data, i, cumulativeFundingRateDelta);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return 2
         + 16
         + 16
         + Borsh.lenOptional(clawbackUser, 32)
         + Borsh.lenOptional(clawbackUserPayment, 16)
         + 16;
  }
}
