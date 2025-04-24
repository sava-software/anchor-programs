package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// The account that validators register as **tip_receiver** with the tip-payment program.
public record TipDistributionAccount(PublicKey _address,
                                     Discriminator discriminator,
                                     // The validator's vote account, also the recipient of remaining lamports after
                                     // upon closing this account.
                                     PublicKey validatorVoteAccount,
                                     // The only account authorized to upload a merkle-root for this account.
                                     PublicKey merkleRootUploadAuthority,
                                     // The merkle root used to verify user claims from this account.
                                     MerkleRoot merkleRoot,
                                     // Epoch for which this account was created.
                                     long epochCreatedAt,
                                     // The commission basis points this validator charges.
                                     int validatorCommissionBps,
                                     // The epoch (upto and including) that tip funds can be claimed.
                                     long expiresAt,
                                     // The bump used to generate this account
                                     int bump) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(85, 64, 113, 198, 234, 94, 120, 123);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VALIDATOR_VOTE_ACCOUNT_OFFSET = 8;
  public static final int MERKLE_ROOT_UPLOAD_AUTHORITY_OFFSET = 40;
  public static final int MERKLE_ROOT_OFFSET = 72;

  public static Filter createValidatorVoteAccountFilter(final PublicKey validatorVoteAccount) {
    return Filter.createMemCompFilter(VALIDATOR_VOTE_ACCOUNT_OFFSET, validatorVoteAccount);
  }

  public static Filter createMerkleRootUploadAuthorityFilter(final PublicKey merkleRootUploadAuthority) {
    return Filter.createMemCompFilter(MERKLE_ROOT_UPLOAD_AUTHORITY_OFFSET, merkleRootUploadAuthority);
  }

  public static Filter createMerkleRootFilter(final MerkleRoot merkleRoot) {
    return Filter.createMemCompFilter(MERKLE_ROOT_OFFSET, merkleRoot.writeOptional());
  }

  public static TipDistributionAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TipDistributionAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TipDistributionAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TipDistributionAccount> FACTORY = TipDistributionAccount::read;

  public static TipDistributionAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var validatorVoteAccount = readPubKey(_data, i);
    i += 32;
    final var merkleRootUploadAuthority = readPubKey(_data, i);
    i += 32;
    final var merkleRoot = _data[i++] == 0 ? null : MerkleRoot.read(_data, i);
    if (merkleRoot != null) {
      i += Borsh.len(merkleRoot);
    }
    final var epochCreatedAt = getInt64LE(_data, i);
    i += 8;
    final var validatorCommissionBps = getInt16LE(_data, i);
    i += 2;
    final var expiresAt = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    return new TipDistributionAccount(_address,
                                      discriminator,
                                      validatorVoteAccount,
                                      merkleRootUploadAuthority,
                                      merkleRoot,
                                      epochCreatedAt,
                                      validatorCommissionBps,
                                      expiresAt,
                                      bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    validatorVoteAccount.write(_data, i);
    i += 32;
    merkleRootUploadAuthority.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(merkleRoot, _data, i);
    putInt64LE(_data, i, epochCreatedAt);
    i += 8;
    putInt16LE(_data, i, validatorCommissionBps);
    i += 2;
    putInt64LE(_data, i, expiresAt);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + (merkleRoot == null ? 1 : (1 + Borsh.len(merkleRoot)))
         + 8
         + 2
         + 8
         + 1;
  }
}
