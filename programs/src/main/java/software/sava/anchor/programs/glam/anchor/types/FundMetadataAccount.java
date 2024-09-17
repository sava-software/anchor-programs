package software.sava.anchor.programs.glam.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record FundMetadataAccount(PublicKey _address,
                                  Discriminator discriminator,
                                  PublicKey fundPubkey,
                                  CompanyField[] company,
                                  FundField[] fund,
                                  ShareClassField[][] shareClasses,
                                  FundManagerField[][] fundManagers) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(214, 24, 35, 92, 16, 104, 166, 6);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int FUND_PUBKEY_OFFSET = 8;
  public static final int COMPANY_OFFSET = 40;

  public static Filter createFundPubkeyFilter(final PublicKey fundPubkey) {
    return Filter.createMemCompFilter(FUND_PUBKEY_OFFSET, fundPubkey);
  }

  public static FundMetadataAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FundMetadataAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FundMetadataAccount> FACTORY = FundMetadataAccount::read;

  public static FundMetadataAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var fundPubkey = readPubKey(_data, i);
    i += 32;
    final var company = Borsh.readVector(CompanyField.class, CompanyField::read, _data, i);
    i += Borsh.lenVector(company);
    final var fund = Borsh.readVector(FundField.class, FundField::read, _data, i);
    i += Borsh.lenVector(fund);
    final var shareClasses = Borsh.readMultiDimensionVector(ShareClassField.class, ShareClassField::read, _data, i);
    i += Borsh.lenVector(shareClasses);
    final var fundManagers = Borsh.readMultiDimensionVector(FundManagerField.class, FundManagerField::read, _data, i);
    return new FundMetadataAccount(_address,
                                   discriminator,
                                   fundPubkey,
                                   company,
                                   fund,
                                   shareClasses,
                                   fundManagers);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    fundPubkey.write(_data, i);
    i += 32;
    i += Borsh.writeVector(company, _data, i);
    i += Borsh.writeVector(fund, _data, i);
    i += Borsh.writeVector(shareClasses, _data, i);
    i += Borsh.writeVector(fundManagers, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + Borsh.lenVector(company)
         + Borsh.lenVector(fund)
         + Borsh.lenVector(shareClasses)
         + Borsh.lenVector(fundManagers);
  }
}
