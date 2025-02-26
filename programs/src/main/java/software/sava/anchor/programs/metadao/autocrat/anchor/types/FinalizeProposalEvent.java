package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record FinalizeProposalEvent(CommonFields common,
                                    PublicKey proposal,
                                    PublicKey dao,
                                    BigInteger passMarketTwap,
                                    BigInteger failMarketTwap,
                                    BigInteger threshold,
                                    ProposalState state) implements Borsh {

  public static final int BYTES = 129;

  public static FinalizeProposalEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var dao = readPubKey(_data, i);
    i += 32;
    final var passMarketTwap = getInt128LE(_data, i);
    i += 16;
    final var failMarketTwap = getInt128LE(_data, i);
    i += 16;
    final var threshold = getInt128LE(_data, i);
    i += 16;
    final var state = ProposalState.read(_data, i);
    return new FinalizeProposalEvent(common,
                                     proposal,
                                     dao,
                                     passMarketTwap,
                                     failMarketTwap,
                                     threshold,
                                     state);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    proposal.write(_data, i);
    i += 32;
    dao.write(_data, i);
    i += 32;
    putInt128LE(_data, i, passMarketTwap);
    i += 16;
    putInt128LE(_data, i, failMarketTwap);
    i += 16;
    putInt128LE(_data, i, threshold);
    i += 16;
    i += Borsh.write(state, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
