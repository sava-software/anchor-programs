package software.sava.anchor.programs.glam.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Account: Governor
public record Governor(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey base,
                       int bump,
                       long proposalCount,
                       PublicKey locker,
                       PublicKey smartWallet,
                       GovernanceParameters params,
                       VotingReward votingReward,
                       BigInteger[] buffers) implements Borsh {

  public static final int BYTES = 729;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(37, 136, 44, 80, 68, 85, 213, 178);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int BASE_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int PROPOSAL_COUNT_OFFSET = 41;
  public static final int LOCKER_OFFSET = 49;
  public static final int SMART_WALLET_OFFSET = 81;
  public static final int PARAMS_OFFSET = 113;
  public static final int VOTING_REWARD_OFFSET = 145;
  public static final int BUFFERS_OFFSET = 217;

  public static Filter createBaseFilter(final PublicKey base) {
    return Filter.createMemCompFilter(BASE_OFFSET, base);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createProposalCountFilter(final long proposalCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, proposalCount);
    return Filter.createMemCompFilter(PROPOSAL_COUNT_OFFSET, _data);
  }

  public static Filter createLockerFilter(final PublicKey locker) {
    return Filter.createMemCompFilter(LOCKER_OFFSET, locker);
  }

  public static Filter createSmartWalletFilter(final PublicKey smartWallet) {
    return Filter.createMemCompFilter(SMART_WALLET_OFFSET, smartWallet);
  }

  public static Filter createParamsFilter(final GovernanceParameters params) {
    return Filter.createMemCompFilter(PARAMS_OFFSET, params.write());
  }

  public static Filter createVotingRewardFilter(final VotingReward votingReward) {
    return Filter.createMemCompFilter(VOTING_REWARD_OFFSET, votingReward.write());
  }

  public static Governor read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Governor read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Governor read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Governor> FACTORY = Governor::read;

  public static Governor read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var base = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var proposalCount = getInt64LE(_data, i);
    i += 8;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var smartWallet = readPubKey(_data, i);
    i += 32;
    final var params = GovernanceParameters.read(_data, i);
    i += Borsh.len(params);
    final var votingReward = VotingReward.read(_data, i);
    i += Borsh.len(votingReward);
    final var buffers = new BigInteger[32];
    Borsh.readArray(buffers, _data, i);
    return new Governor(_address,
                        discriminator,
                        base,
                        bump,
                        proposalCount,
                        locker,
                        smartWallet,
                        params,
                        votingReward,
                        buffers);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    base.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, proposalCount);
    i += 8;
    locker.write(_data, i);
    i += 32;
    smartWallet.write(_data, i);
    i += 32;
    i += Borsh.write(params, _data, i);
    i += Borsh.write(votingReward, _data, i);
    i += Borsh.writeArray(buffers, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
