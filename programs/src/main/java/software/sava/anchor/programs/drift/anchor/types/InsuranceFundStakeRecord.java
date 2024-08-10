package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InsuranceFundStakeRecord(long ts,
                                       PublicKey userAuthority,
                                       StakeAction action,
                                       long amount,
                                       int marketIndex,
                                       long insuranceVaultAmountBefore,
                                       BigInteger ifSharesBefore,
                                       BigInteger userIfSharesBefore,
                                       BigInteger totalIfSharesBefore,
                                       BigInteger ifSharesAfter,
                                       BigInteger userIfSharesAfter,
                                       BigInteger totalIfSharesAfter) implements Borsh {

  public static final int BYTES = 155;

  public static InsuranceFundStakeRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var userAuthority = readPubKey(_data, i);
    i += 32;
    final var action = StakeAction.read(_data, i);
    i += Borsh.len(action);
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var insuranceVaultAmountBefore = getInt64LE(_data, i);
    i += 8;
    final var ifSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var userIfSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var totalIfSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var ifSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var userIfSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var totalIfSharesAfter = getInt128LE(_data, i);
    return new InsuranceFundStakeRecord(ts,
                                        userAuthority,
                                        action,
                                        amount,
                                        marketIndex,
                                        insuranceVaultAmountBefore,
                                        ifSharesBefore,
                                        userIfSharesBefore,
                                        totalIfSharesBefore,
                                        ifSharesAfter,
                                        userIfSharesAfter,
                                        totalIfSharesAfter);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    userAuthority.write(_data, i);
    i += 32;
    i += Borsh.write(action, _data, i);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, insuranceVaultAmountBefore);
    i += 8;
    putInt128LE(_data, i, ifSharesBefore);
    i += 16;
    putInt128LE(_data, i, userIfSharesBefore);
    i += 16;
    putInt128LE(_data, i, totalIfSharesBefore);
    i += 16;
    putInt128LE(_data, i, ifSharesAfter);
    i += 16;
    putInt128LE(_data, i, userIfSharesAfter);
    i += 16;
    putInt128LE(_data, i, totalIfSharesAfter);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 32
         + Borsh.len(action)
         + 8
         + 2
         + 8
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16;
  }
}