package software.sava.anchor.programs.metadao.autocrat.anchor.types;

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

public record InitializeDaoEvent(CommonFields common,
                                 PublicKey dao,
                                 PublicKey baseMint,
                                 PublicKey quoteMint,
                                 int passThresholdBps,
                                 long slotsPerProposal,
                                 BigInteger twapInitialObservation,
                                 BigInteger twapMaxObservationChangePerUpdate,
                                 long minQuoteFutarchicLiquidity,
                                 long minBaseFutarchicLiquidity,
                                 InitialSpendingLimit initialSpendingLimit,
                                 PublicKey squadsMultisig,
                                 PublicKey squadsMultisigVault) implements Borsh {

  public static InitializeDaoEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var dao = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var passThresholdBps = getInt16LE(_data, i);
    i += 2;
    final var slotsPerProposal = getInt64LE(_data, i);
    i += 8;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var minQuoteFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var minBaseFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var initialSpendingLimit = _data[i++] == 0 ? null : InitialSpendingLimit.read(_data, i);
    if (initialSpendingLimit != null) {
      i += Borsh.len(initialSpendingLimit);
    }
    final var squadsMultisig = readPubKey(_data, i);
    i += 32;
    final var squadsMultisigVault = readPubKey(_data, i);
    return new InitializeDaoEvent(common,
                                  dao,
                                  baseMint,
                                  quoteMint,
                                  passThresholdBps,
                                  slotsPerProposal,
                                  twapInitialObservation,
                                  twapMaxObservationChangePerUpdate,
                                  minQuoteFutarchicLiquidity,
                                  minBaseFutarchicLiquidity,
                                  initialSpendingLimit,
                                  squadsMultisig,
                                  squadsMultisigVault);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    dao.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    putInt16LE(_data, i, passThresholdBps);
    i += 2;
    putInt64LE(_data, i, slotsPerProposal);
    i += 8;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, minQuoteFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, minBaseFutarchicLiquidity);
    i += 8;
    i += Borsh.writeOptional(initialSpendingLimit, _data, i);
    squadsMultisig.write(_data, i);
    i += 32;
    squadsMultisigVault.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + 32
         + 32
         + 2
         + 8
         + 16
         + 16
         + 8
         + 8
         + (initialSpendingLimit == null ? 1 : (1 + Borsh.len(initialSpendingLimit)))
         + 32
         + 32;
  }
}
